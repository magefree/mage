package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SpectralShepherd extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);

    public SpectralShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}: Return target Spirit you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private SpectralShepherd(final SpectralShepherd card) {
        super(card);
    }

    @Override
    public SpectralShepherd copy() {
        return new SpectralShepherd(this);
    }
}
