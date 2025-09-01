package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class PhyrexianTotem extends CardImpl {

    public PhyrexianTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{B}: {this} becomes a 5/5 black Horror artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new PhyrexianTotemToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}")));

        // Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENTS, SavedDamageValue.MANY, ""), false
        ).withInterveningIf(PhyrexianTotemCondition.instance).setTriggerPhrase("Whenever this permanent is dealt damage, "));
    }

    private PhyrexianTotem(final PhyrexianTotem card) {
        super(card);
    }

    @Override
    public PhyrexianTotem copy() {
        return new PhyrexianTotem(this);
    }

    private static class PhyrexianTotemToken extends TokenImpl {
        PhyrexianTotemToken() {
            super("Phyrexian Horror", "5/5 black Phyrexian Horror artifact creature with trample");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlack(true);
            this.subtype.add(SubType.PHYREXIAN);
            this.subtype.add(SubType.HORROR);
            power = new MageInt(5);
            toughness = new MageInt(5);
            this.addAbility(TrampleAbility.getInstance());
        }

        private PhyrexianTotemToken(final PhyrexianTotemToken token) {
            super(token);
        }

        public PhyrexianTotemToken copy() {
            return new PhyrexianTotemToken(this);
        }
    }
}

enum PhyrexianTotemCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && permanent.isCreature(game);
    }

    @Override
    public String toString() {
        return "it's a creature";
    }
}
