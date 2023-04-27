package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeriaScholarOfAntiquity extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("untapped nontoken artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(TokenPredicate.FALSE);
    }

    public MeriaScholarOfAntiquity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap an untapped nontoken artifact you control: Add {G}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.GreenMana(1),
                new TapTargetCost(new TargetControlledPermanent(filter))
        ));

        // Tap two untapped nontoken artifacts you control: Exile the top card of your library. You may play it this turn.
        this.addAbility(new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1),
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private MeriaScholarOfAntiquity(final MeriaScholarOfAntiquity card) {
        super(card);
    }

    @Override
    public MeriaScholarOfAntiquity copy() {
        return new MeriaScholarOfAntiquity(this);
    }
}
