package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author earchip94
 */
public final class ClementTheWorrywort extends CardImpl {

    private static final FilterPermanent frogFilter = new FilterPermanent(SubType.FROG, "Frogs");

    public ClementTheWorrywort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Clement, the Worrywort or another creature you control enters, return up to one target creature you control with lesser mana value to its owner's hand.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new ClementWorrywortEffect(), new FilterCreaturePermanent(), false, true));

        // Frogs you control have "{T}: Add {G} or {U}. Spend this mana only to cast a creature spell."
        Ability gMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(1), new ClementWorrywortManaBuilder());
        Ability bMana = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.BlueMana(1), new ClementWorrywortManaBuilder());
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(gMana, Duration.WhileOnBattlefield, frogFilter, false)
        ));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(bMana, Duration.WhileOnBattlefield, frogFilter, false)
        ));
    }

    private ClementTheWorrywort(final ClementTheWorrywort card) {
        super(card);
    }

    @Override
    public ClementTheWorrywort copy() {
        return new ClementTheWorrywort(this);
    }
}

class ClementWorrywortEffect extends OneShotEffect {

    ClementWorrywortEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one target creature you control with lesser mana value to its owner's hand.";
    }

    private ClementWorrywortEffect(final ClementWorrywortEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // Get the entering object.
        Object obj = this.getValue("permanentEnteringBattlefield");
        if (!(obj instanceof Permanent)) {
            return false;
        }

        // Etb creature, it's always going to be a creature due to triggered ability filter.
        Permanent permanent = (Permanent) obj;

        FilterCreaturePermanent bounceCreature = new FilterCreaturePermanent(
                "a creature you control with mana value " + permanent.getManaValue() + " or less"
        );
        // Can only bounce your creatures
        bounceCreature.add(TargetController.YOU.getControllerPredicate());
        // Less than etb card
        bounceCreature.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getManaValue()));

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, bounceCreature, false);
        if (target.canChoose(source.getControllerId(), source, game)) {
            player.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                return false;
            }

            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public OneShotEffect copy() {
        return new ClementWorrywortEffect(this);
    }

}

class ClementWorrywortConditionalMana extends ConditionalMana {

    ClementWorrywortConditionalMana(Mana mana) {
        super(mana);
        setComparisonScope(Filter.ComparisonScope.Any);
        addCondition(new CreatureCastManaCondition());
    }
}

class ClementWorrywortManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ClementWorrywortConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}