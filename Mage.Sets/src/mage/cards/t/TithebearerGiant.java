package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TithebearerGiant extends CardImpl {

    public TithebearerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Tithebearer Giant enters the battlefield, you draw a card and you lose 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TithebearerGiant(final TithebearerGiant card) {
        super(card);
    }

    @Override
    public TithebearerGiant copy() {
        return new TithebearerGiant(this);
    }
}
