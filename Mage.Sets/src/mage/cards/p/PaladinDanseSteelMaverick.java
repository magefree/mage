package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author justinjohnson14
 */
public final class PaladinDanseSteelMaverick extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HUMAN.getPredicate()
        ));
    }

    public PaladinDanseSteelMaverick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Exile Paladin Danse, Steel Maverick: Each creature you control that's an artifact or Human gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityAllEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter).setText("Each creature you control that's an artifact creature or Human gains indestructible until end of turn"),
                new ExileSourceCost()
        ));
    }

    private PaladinDanseSteelMaverick(final PaladinDanseSteelMaverick card) {
        super(card);
    }

    @Override
    public PaladinDanseSteelMaverick copy() {
        return new PaladinDanseSteelMaverick(this);
    }
}
