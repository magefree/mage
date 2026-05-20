package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InklingMascot extends CardImpl {

    public InklingMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.INKLING);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, this creature gains flying until end of turn. Surveil 1.
        Ability ability = new ReparteeAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        ability.addEffect(new SurveilEffect(1));
        this.addAbility(ability);
    }

    private InklingMascot(final InklingMascot card) {
        super(card);
    }

    @Override
    public InklingMascot copy() {
        return new InklingMascot(this);
    }
}
