package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author weirddan455
 */
public final class DoggedDetective extends CardImpl {

    public DoggedDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Dogged Detective enters the battlefield, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // Whenever an opponent draws their second card each turn, you may return Dogged Detective from your graveyard to your hand.
        this.addAbility(new DrawCardTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), true, TargetController.OPPONENT, 2));
    }

    private DoggedDetective(final DoggedDetective card) {
        super(card);
    }

    @Override
    public DoggedDetective copy() {
        return new DoggedDetective(this);
    }
}
