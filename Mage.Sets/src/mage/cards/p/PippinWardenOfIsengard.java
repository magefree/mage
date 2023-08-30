package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PippinWardenOfIsengard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public PippinWardenOfIsengard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Merry, Warden of Isengard
        this.addAbility(new PartnerWithAbility("Merry, Warden of Isengard"));

        // {1}, {T}: Create a Food token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new FoodToken()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Sacrifice four Foods: Other creatures you control get +3/+3 and gain haste until end of turn. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new BoostControlledEffect(
                3, 3, Duration.EndOfTurn, true
        ).setText("other creatures you control get +3/+3"), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(4, filter)));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);
    }

    private PippinWardenOfIsengard(final PippinWardenOfIsengard card) {
        super(card);
    }

    @Override
    public PippinWardenOfIsengard copy() {
        return new PippinWardenOfIsengard(this);
    }
}
