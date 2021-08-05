package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoneriseSpirit extends CardImpl {

    public StoneriseSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {4}, Exile a card from your graveyard: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(4));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD
        )));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private StoneriseSpirit(final StoneriseSpirit card) {
        super(card);
    }

    @Override
    public StoneriseSpirit copy() {
        return new StoneriseSpirit(this);
    }
}
