package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author jimga150
 */
public final class BronzebeakForagers extends CardImpl {

    public BronzebeakForagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Bronzebeak Foragers enters the battlefield, for each opponent, exile up to one target nonland permanent
        // that player controls until Bronzebeak Foragers leaves the battlefield.
        // The next few lines are taken from Grasp of Fate
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, exile up to one target nonland permanent that player controls until {this} leaves the battlefield")
        );
        etbAbility.addTarget(new TargetNonlandPermanent(0, 1));
        etbAbility.setTargetAdjuster(new ForEachOpponentTargetsAdjuster());
        this.addAbility(etbAbility);

        // {X}{W}: Put target card with mana value X exiled with Bronzebeak Foragers into its owner's graveyard.
        // You gain X life.
        // Based on Gelatinous Cube's "Dissolve" ability
        Ability dissolveAbility = new SimpleActivatedAbility(
                new BronzebeakForagerDissolveEffect(),
                new ManaCostsImpl<>("{X}{W}")
        );
        dissolveAbility.setTargetAdjuster(BronzebeakForagerDissolveAdjuster.instance);
        dissolveAbility.addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR));
        this.addAbility(dissolveAbility);
    }

    private BronzebeakForagers(final BronzebeakForagers card) {
        super(card);
    }

    @Override
    public BronzebeakForagers copy() {
        return new BronzebeakForagers(this);
    }
}

// Based on Gelatinous Cube
enum BronzebeakForagerDissolveAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCard("card with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCardInExile(filter, CardUtil.getExileZoneId(game, ability)));
    }
}

// Based on Gelatinous Cube
class BronzebeakForagerDissolveEffect extends OneShotEffect {

    BronzebeakForagerDissolveEffect() {
        super(Outcome.Benefit);
        staticText = "put target card with mana value X exiled with {this} into its owner's graveyard";
    }

    private BronzebeakForagerDissolveEffect(final BronzebeakForagerDissolveEffect effect) {
        super(effect);
    }

    @Override
    public BronzebeakForagerDissolveEffect copy() {
        return new BronzebeakForagerDissolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player != null && card != null){
            return player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return false;
    }
}
