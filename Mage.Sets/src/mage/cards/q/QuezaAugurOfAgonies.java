package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuezaAugurOfAgonies extends CardImpl {

    public QuezaAugurOfAgonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you draw a card, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DrawCardControllerTriggeredAbility(new LoseLifeTargetEffect(1), false);
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private QuezaAugurOfAgonies(final QuezaAugurOfAgonies card) {
        super(card);
    }

    @Override
    public QuezaAugurOfAgonies copy() {
        return new QuezaAugurOfAgonies(this);
    }
}
