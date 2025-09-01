package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PlayLandOrCastSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class InfernalSovereign extends CardImpl {

    public InfernalSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // Whenever you play a land or cast a spell, you draw a card and you lose 1 life.
        Ability ability = new PlayLandOrCastSpellTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private InfernalSovereign(final InfernalSovereign card) {
        super(card);
    }

    @Override
    public InfernalSovereign copy() {
        return new InfernalSovereign(this);
    }
}
