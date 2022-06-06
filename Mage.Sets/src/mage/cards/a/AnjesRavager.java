package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnjesRavager extends CardImpl {

    public AnjesRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Anje's Ravager attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Anje's Ravager attacks, discard your hand, then draw three cards.
        Ability ability = new AttacksTriggeredAbility(
                new DiscardHandControllerEffect().setText("discard your hand,"), false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy("then"));
        this.addAbility(ability);

        // Madness {1}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private AnjesRavager(final AnjesRavager card) {
        super(card);
    }

    @Override
    public AnjesRavager copy() {
        return new AnjesRavager(this);
    }
}
