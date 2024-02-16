package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldFromGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianDragonEngine extends CardImpl {

    public PhyrexianDragonEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.meldsWithClazz = mage.cards.m.MishraClaimedByGix.class;
        this.meldsToClazz = mage.cards.m.MishraLostToPhyrexia.class;

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Phyrexian Dragon Engine enters the battlefield from your graveyard, you may discard your hand. If you do, draw three cards.
        this.addAbility(new EntersBattlefieldFromGraveyardTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(3), new DiscardHandCost()
                ), TargetController.YOU
        ));

        // Unearth {3}{R}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{R}{R}")));

        // (Melds with Mishra, Claimed by Gix.)
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("<i>(Melds with Mishra, Claimed by Gix.)</i>")
        ));
    }

    private PhyrexianDragonEngine(final PhyrexianDragonEngine card) {
        super(card);
    }

    @Override
    public PhyrexianDragonEngine copy() {
        return new PhyrexianDragonEngine(this);
    }
}
