package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KomasFaithful extends CardImpl {

    public KomasFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Koma's Faithful dies, each player mills three cards.
        this.addAbility(new DiesSourceTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER)
        ));
    }

    private KomasFaithful(final KomasFaithful card) {
        super(card);
    }

    @Override
    public KomasFaithful copy() {
        return new KomasFaithful(this);
    }
}
