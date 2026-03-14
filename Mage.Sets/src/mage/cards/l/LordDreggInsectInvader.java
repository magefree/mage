package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.InsectWarriorToken;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordDreggInsectInvader extends CardImpl {

    public LordDreggInsectInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, create a 1/1 black Insect Warrior creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new InsectWarriorToken()))
                .withInterveningIf(RevoltCondition.instance)
                .addHint(RevoltCondition.getHint())
                .setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());

        // {3}{G}, Sacrifice a token: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_TOKEN));
        this.addAbility(ability);
    }

    private LordDreggInsectInvader(final LordDreggInsectInvader card) {
        super(card);
    }

    @Override
    public LordDreggInsectInvader copy() {
        return new LordDreggInsectInvader(this);
    }
}
