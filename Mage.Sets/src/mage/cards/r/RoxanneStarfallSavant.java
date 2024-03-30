package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MeteoriteToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RoxanneStarfallSavant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("you tap an artifact token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public RoxanneStarfallSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Roxanne, Starfall Savant enters the battlefield or attacks, create a tapped colorless artifact token named Meteorite with "When Meteorite enters the battlefield, it deals 2 damage to any target" and "{T}: Add one mana of any color."
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new MeteoriteToken(), 1, true)));

        // Whenever you tap an artifact token for mana, add one mana of any type that artifact token produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(), filter,
                SetTargetPointer.PERMANENT
        ));
    }

    private RoxanneStarfallSavant(final RoxanneStarfallSavant card) {
        super(card);
    }

    @Override
    public RoxanneStarfallSavant copy() {
        return new RoxanneStarfallSavant(this);
    }
}
