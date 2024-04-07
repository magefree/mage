package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperityTycoon extends CardImpl {

    public ProsperityTycoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Prosperity Tycoon enters the battlefield, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));

        // {2}, Sacrifice a token: Prosperity Tycoon gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_TOKEN));
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private ProsperityTycoon(final ProsperityTycoon card) {
        super(card);
    }

    @Override
    public ProsperityTycoon copy() {
        return new ProsperityTycoon(this);
    }
}
