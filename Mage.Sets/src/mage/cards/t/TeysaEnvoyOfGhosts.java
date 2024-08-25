package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.WhiteBlackSpiritToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TeysaEnvoyOfGhosts extends CardImpl {

    public TeysaEnvoyOfGhosts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // protection from creatures
        this.addAbility(new ProtectionAbility(new FilterCreatureCard("creatures")));

        // Whenever a creature deals combat damage to you, destroy that creature. Create a 1/1 white and black Spirit creature token with flying.
        Ability ability = new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT_CREATURE,
                new DestroyTargetEffect(), true);
        ability.addEffect(new CreateTokenEffect(new WhiteBlackSpiritToken(), 1));
        this.addAbility(ability);

    }

    private TeysaEnvoyOfGhosts(final TeysaEnvoyOfGhosts card) {
        super(card);
    }

    @Override
    public TeysaEnvoyOfGhosts copy() {
        return new TeysaEnvoyOfGhosts(this);
    }
}
