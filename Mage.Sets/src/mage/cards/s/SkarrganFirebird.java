
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.BloodthirstWatcher;

/**
 *
 * @author LevelX2
 */
public final class SkarrganFirebird extends CardImpl {

    public SkarrganFirebird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodthirst 3
        this.addAbility(new BloodthirstAbility(3));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}{R}{R}: Return Skarrgan Firebird from your graveyard to your hand. Activate this ability only if an opponent was dealt damage this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{R}{R}{R}"),
                new OpponentWasDealtDamageCondition(),
                null));
    }

    private SkarrganFirebird(final SkarrganFirebird card) {
        super(card);
    }

    @Override
    public SkarrganFirebird copy() {
        return new SkarrganFirebird(this);
    }
}

class OpponentWasDealtDamageCondition implements Condition {

    public OpponentWasDealtDamageCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        BloodthirstWatcher watcher = game.getState().getWatcher(BloodthirstWatcher.class, source.getControllerId());
        return watcher != null && watcher.conditionMet();
    }

    @Override
    public String toString() {
	return "if an opponent was dealt damage this turn";
    }
}
