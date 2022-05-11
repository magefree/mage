package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth, TheElk801
 */
public final class HakimLoreweaver extends CardImpl {

    private static final FilterCard filter = new FilterCard("target Aura card from your graveyard");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(SubType.AURA.getPredicate());
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public HakimLoreweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}{U}: Return target Aura card from your graveyard to the battlefield attached to Hakim, Loreweaver. Activate this ability only during your upkeep and only if Hakim isn't enchanted.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new HakimLoreweaverEffect(),
                new ManaCostsImpl<>("{U}{U}"),
                HakimLoreweaverCondition.instance
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // {U}{U}, {tap}: Destroy all Auras attached to Hakim.
        Ability ability2 = new SimpleActivatedAbility(new HakimLoreweaverEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    private HakimLoreweaver(final HakimLoreweaver card) {
        super(card);
    }

    @Override
    public HakimLoreweaver copy() {
        return new HakimLoreweaver(this);
    }
}

class HakimLoreweaverEffect extends OneShotEffect {

    public HakimLoreweaverEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target Aura card from your graveyard to the battlefield attached to {this}.";
    }

    public HakimLoreweaverEffect(final HakimLoreweaverEffect effect) {
        super(effect);
    }

    @Override
    public HakimLoreweaverEffect copy() {
        return new HakimLoreweaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent hakimLoreweaver = game.getPermanent(source.getSourceId());
        Card targetAuraCard = game.getCard(source.getFirstTarget());
        if (controller != null
                && hakimLoreweaver != null
                && controller.canRespond()
                && targetAuraCard != null
                && new AuraCardCanAttachToPermanentId(hakimLoreweaver.getId()).apply(targetAuraCard, game)) {
            Target target = targetAuraCard.getSpellAbility().getTargets().get(0);
            if (target != null) {
                game.getState().setValue("attachTo:" + targetAuraCard.getId(), hakimLoreweaver);
                controller.moveCards(targetAuraCard, Zone.BATTLEFIELD, source, game);
                return hakimLoreweaver.addAttachment(targetAuraCard.getId(), source, game);
            }
        }
        return false;
    }
}

enum HakimLoreweaverCondition implements Condition {
    instance;
    static private final FilterPermanent auras = new FilterPermanent();

    static {
        auras.add(CardType.ENCHANTMENT.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (PhaseStep.UPKEEP != game.getStep().getType()
                || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        Permanent hakimLoreweaver = source.getSourcePermanentIfItStillExists(game);
        return hakimLoreweaver != null
                && hakimLoreweaver
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.AURA, game));
    }

    @Override
    public String toString() {
        return "during your upkeep and only if {this} isn't enchanted";
    }
}

class HakimLoreweaverDestroyEffect extends OneShotEffect {

    HakimLoreweaverDestroyEffect() {
        super(Outcome.Benefit);
        staticText = "destroy all Auras attached to {this}";
    }

    private HakimLoreweaverDestroyEffect(final HakimLoreweaverDestroyEffect effect) {
        super(effect);
    }

    @Override
    public HakimLoreweaverDestroyEffect copy() {
        return new HakimLoreweaverDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(SubType.AURA.getPredicate());
        filter.add(new HakimLoreweaverPredicate(permanent));
        return new DestroyAllEffect(filter).apply(game, source);
    }
}

class HakimLoreweaverPredicate implements Predicate<Permanent> {

    private final Permanent permanent;

    HakimLoreweaverPredicate(Permanent permanent) {
        this.permanent = permanent;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isAttachedTo(permanent.getId());
    }
}