package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MantleOfTheAncients extends CardImpl {
    private static final FilterCard filter = new FilterPermanentCard();

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public MantleOfTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Mantle of the Ancients enters the battlefield, return any number of target Aura and/or Equipment cards from your graveyard to the battlefield attached to enchanted creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MantleOfTheAncientsEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(
                MantleOfTheAncientsValue.instance, MantleOfTheAncientsValue.instance
        )));
    }

    private MantleOfTheAncients(final MantleOfTheAncients card) {
        super(card);
    }

    @Override
    public MantleOfTheAncients copy() {
        return new MantleOfTheAncients(this);
    }
}

class MantleOfTheAncientsEffect extends OneShotEffect {

    MantleOfTheAncientsEffect() {
        super(Outcome.Benefit);
        staticText = "return any number of target Aura and/or Equipment cards " +
                "from your graveyard to the battlefield attached to enchanted creature";
    }

    private MantleOfTheAncientsEffect(final MantleOfTheAncientsEffect effect) {
        super(effect);
    }

    @Override
    public MantleOfTheAncientsEffect copy() {
        return new MantleOfTheAncientsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (player == null || sourcePermanent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        Set<Card> cards = getTargetPointer().getTargets(game, source).stream().map(game::getCard).filter(Objects::nonNull)
                .filter(card -> !permanent.cantBeAttachedBy(card, source, game, true)).collect(Collectors.toSet());
        if (cards.isEmpty()) {
            return false;
        }

        cards.forEach(card -> game.getState().setValue("attachTo:" + card.getId(), permanent));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        Cards movedCards = new CardsImpl(cards);
        movedCards.retainZone(Zone.BATTLEFIELD, game);
        movedCards.forEach(card -> permanent.addAttachment(card, source, game));
        return true;
    }
}

enum MantleOfTheAncientsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        return permanent == null ? 0 : permanent
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(p -> p.hasSubtype(SubType.EQUIPMENT, game) || p.hasSubtype(SubType.AURA, game))
                .mapToInt(b -> b ? 1 : 0)
                .sum();
    }

    @Override
    public MantleOfTheAncientsValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "Aura and Equipment attached to it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
