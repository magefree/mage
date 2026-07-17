package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkycoachConductor extends PrepareCard {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("non-Pilot creature you control");

    static {
        filter.add(Predicates.not(SubType.PILOT.getPredicate()));
    }

    public SkycoachConductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}", "All Aboard", new CardType[]{CardType.INSTANT}, "{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // All Aboard
        // Instant {U}
        // Exile target non-Pilot creature you control, then return that card to the battlefield under its owner's control.
        this.getSpellCard().getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, true));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SkycoachConductor(final SkycoachConductor card) {
        super(card);
    }

    @Override
    public SkycoachConductor copy() {
        return new SkycoachConductor(this);
    }
}
