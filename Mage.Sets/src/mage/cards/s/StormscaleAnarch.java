package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class StormscaleAnarch extends CardImpl {

    public StormscaleAnarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}, Discard a card at random: Stormscale Anarch deals 2 damage to any target. If the discarded card was multicolored, Stormscale Anarch deals 4 damage to that creature or player instead.
        Ability ability = new SimpleActivatedAbility(new StormscaleAnarchEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new DiscardCardCost(true));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private StormscaleAnarch(final StormscaleAnarch card) {
        super(card);
    }

    @Override
    public StormscaleAnarch copy() {
        return new StormscaleAnarch(this);
    }
}

class StormscaleAnarchEffect extends OneShotEffect {

    public StormscaleAnarchEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals 2 damage to any target. "
                + "If the discarded card was multicolored, "
                + "{this} deals 4 damage instead.";
    }

    public StormscaleAnarchEffect(final StormscaleAnarchEffect effect) {
        super(effect);
    }

    @Override
    public StormscaleAnarchEffect copy() {
        return new StormscaleAnarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damageToDeal = 2;
        outerloop:
        for (Cost cost : source.getCosts()) {
            if (cost instanceof DiscardCardCost) {
                for (Card card : ((DiscardCardCost) cost).getCards()) {
                    if (card.getColor(game).isMulticolored()) {
                        damageToDeal = 4;
                        break outerloop;
                    }
                }
            }
        }
        return new DamageTargetEffect(damageToDeal).apply(game, source);
    }
}
