package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.d.DeathbonnetHulk;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

public final class BanebladeScoundrel extends CardImpl {

    public static final String CARDNAME = "Baneblade Scoundrel";

    public BanebladeScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = BaneclawMarauder.class;

        // Whenever Baneblade Scoundrel becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false));

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private BanebladeScoundrel(final BanebladeScoundrel card) {
        super(card);
    }

    @Override
    public BanebladeScoundrel copy() {
        return new BanebladeScoundrel(this);
    }
}
