package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainDevil extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public ChainDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Animate Chains — When Chain Devil enters the battlefield, each player sacrifices a nontoken creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeAllEffect(1, filter)
        ).withFlavorWord("Animate Chains"));
    }

    private ChainDevil(final ChainDevil card) {
        super(card);
    }

    @Override
    public ChainDevil copy() {
        return new ChainDevil(this);
    }
}
