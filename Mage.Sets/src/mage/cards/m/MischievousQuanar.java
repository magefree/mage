
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class MischievousQuanar extends CardImpl {

    public MischievousQuanar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {3}{U}{U}: Turn Mischievous Quanar face down.
        Effect effect = new BecomesFaceDownCreatureEffect(Duration.Custom, BecomesFaceDownCreatureEffect.FaceDownType.MANUAL);
        effect.setText("Turn {this} face down. <i>(It becomes a 2/2 creature.)</i>");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}{U}{U}")));

        // Morph {1}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{U}{U}")));

        // When Mischievous Quanar is turned face up, copy target instant or sorcery spell. You may choose new targets for that copy.
        Effect effect2 = new CopyTargetSpellEffect();
        effect2.setText("copy target instant or sorcery spell. You may choose new targets for that copy");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect2);
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private MischievousQuanar(final MischievousQuanar card) {
        super(card);
    }

    @Override
    public MischievousQuanar copy() {
        return new MischievousQuanar(this);
    }
}
