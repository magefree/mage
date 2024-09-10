package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JirinaDauntlessGeneral extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "");

    public JirinaDauntlessGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Jirina, Dauntless General enters the battlefield, exile target player's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllTargetPlayerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Sacrifice Jirina: Humans you control gain hexproof and indestructible until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("Humans you control gain hexproof"), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and indestructible until end of turn"));
        this.addAbility(ability);
    }

    private JirinaDauntlessGeneral(final JirinaDauntlessGeneral card) {
        super(card);
    }

    @Override
    public JirinaDauntlessGeneral copy() {
        return new JirinaDauntlessGeneral(this);
    }
}
