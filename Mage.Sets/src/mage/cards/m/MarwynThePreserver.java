package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.MageInt;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class MarwynThePreserver extends CardImpl {

    public MarwynThePreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lands you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS
        )));

        // {2}: Return target land card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND));
        this.addAbility(ability);
    }

    private MarwynThePreserver(final MarwynThePreserver card) {
        super(card);
    }

    @Override
    public MarwynThePreserver copy() {
        return new MarwynThePreserver(this);
    }
}
