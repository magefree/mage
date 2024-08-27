package mage.cards.c;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CodsworthHandyHelper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("commanders");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Aura or Equipment you control");

    static {
        filter.add(CommanderPredicate.instance);
        filter2.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public CodsworthHandyHelper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Commanders you control have ward {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield, filter
        )));

        // {T}: Add {W}{W}. Spend this mana only to cast Aura and/or Equipment spells.
        this.addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(), Mana.WhiteMana(2),
                new CodsworthHandyHelperManaBuilder()
        ));

        // {T}: Attach target Aura or Equipment you control to target creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new CodsworthHandyHelperEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private CodsworthHandyHelper(final CodsworthHandyHelper card) {
        super(card);
    }

    @Override
    public CodsworthHandyHelper copy() {
        return new CodsworthHandyHelper(this);
    }
}

class CodsworthHandyHelperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CodsworthHandyHelperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Aura and/or Equipment spells";
    }
}

class CodsworthHandyHelperConditionalMana extends ConditionalMana {

    public CodsworthHandyHelperConditionalMana(Mana mana) {
        super(mana);
        addCondition(new CodsworthHandyHelperManaCondition());
    }

    private CodsworthHandyHelperConditionalMana(final CodsworthHandyHelperConditionalMana conditionalMana) {
        super(conditionalMana);
    }

    @Override
    public CodsworthHandyHelperConditionalMana copy() {
        return new CodsworthHandyHelperConditionalMana(this);
    }
}

class CodsworthHandyHelperManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            return card != null && (
                    card.getSubtype(game).contains(SubType.AURA) || card.getSubtype(game).contains(SubType.EQUIPMENT)
            );
        }
        return false;
    }
}
class CodsworthHandyHelperEffect extends OneShotEffect {

    CodsworthHandyHelperEffect() {
        super(Outcome.Benefit);
        staticText = "Attach target Aura or Equipment you control to target creature you control";
    }

    private CodsworthHandyHelperEffect(final CodsworthHandyHelperEffect effect) {
        super(effect);
    }

    @Override
    public CodsworthHandyHelperEffect copy() {
        return new CodsworthHandyHelperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attachment = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller == null || attachment == null || creature == null) {
            return false;
        }

        if (creature.cantBeAttachedBy(attachment, source, game, true)) {
            game.informPlayers(attachment.getLogName() + " was not attached to " + creature.getLogName()
                    + " because it's not a legal target" + CardUtil.getSourceLogName(game, source));
            return false;
        }
        Permanent oldCreature = game.getPermanent(attachment.getAttachedTo());
        if (oldCreature != null) {
            oldCreature.removeAttachment(attachment.getId(), source, game);
        }
        creature.addAttachment(attachment.getId(), source, game);
        game.informPlayers(attachment.getLogName() + " was "
                + (oldCreature != null ? "unattached from " + oldCreature.getLogName() + " and " : "")
                + "attached to " + creature.getLogName()
        );
        return true;
    }
}
