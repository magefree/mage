package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MajesticAuricorn extends CardImpl {

    public MajesticAuricorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {3}{W}
        this.addAbility(new MutateAbility(this, "{3}{W}"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature mutates, you gain 4 life.
        this.addAbility(new MutatesSourceTriggeredAbility(new GainLifeEffect(4)));
    }

    private MajesticAuricorn(final MajesticAuricorn card) {
        super(card);
    }

    @Override
    public MajesticAuricorn copy() {
        return new MajesticAuricorn(this);
    }
}
