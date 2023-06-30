
package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Pronoun;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 * @author LevelX2
 */
public final class KytheonHeroOfAkros extends CardImpl {

    public KytheonHeroOfAkros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.g.GideonBattleForged.class;

        // At end of combat, if Kytheon, Hero of Akros and at least two other creatures attacked this combat, exile Kytheon,
        // then return him to the battlefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EndOfCombatTriggeredAbility(new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED,Pronoun.HE), false),
                new KytheonHeroOfAkrosCondition(), "At end of combat, if {this} and at least two other creatures attacked this combat, exile {this}, "
                + "then return him to the battlefield transformed under his owner's control."), new AttackedOrBlockedThisCombatWatcher());

        // {2}{W}: Kytheon gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{W}")));

    }

    private KytheonHeroOfAkros(final KytheonHeroOfAkros card) {
        super(card);
    }

    @Override
    public KytheonHeroOfAkros copy() {
        return new KytheonHeroOfAkros(this);
    }
}

class KytheonHeroOfAkrosCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
            if (watcher != null) {
                boolean sourceFound = false;
                int number = 0;
                for (MageObjectReference mor : watcher.getAttackedThisTurnCreatures()) {
                    if (mor.refersTo(sourceObject, game)) {
                        sourceFound = true;
                    } else {
                        number++;
                    }
                }
                return sourceFound && number >= 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if {this} and at least two other creatures attacked this combat";
    }
}
