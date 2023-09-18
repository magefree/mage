package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatchetRescueRacer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public RatchetRescueRacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever one or more nontoken artifacts you control are put into a graveyard from the battlefield, convert Ratchet. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}"), false, filter
        ).setTriggerPhrase("Whenever one or more nontoken artifacts you control " +
                "are put into a graveyard from the battlefield, ").setTriggersOnceEachTurn(true));
    }

    private RatchetRescueRacer(final RatchetRescueRacer card) {
        super(card);
    }

    @Override
    public RatchetRescueRacer copy() {
        return new RatchetRescueRacer(this);
    }
}
