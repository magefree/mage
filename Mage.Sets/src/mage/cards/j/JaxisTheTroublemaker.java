package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaxisTheTroublemaker extends CardImpl {

    public JaxisTheTroublemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {R}, {T}, Discard a card: Create a token that's a copy of another target creature you control. It gains haste and "When this creature dies, draw a card." Sacrifice it at the beginning of the next end step. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new JaxisTheTroublemakerEffect(), new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // Blitz {1}{R}
        this.addAbility(new BlitzAbility("{1}{R}"));
    }

    private JaxisTheTroublemaker(final JaxisTheTroublemaker card) {
        super(card);
    }

    @Override
    public JaxisTheTroublemaker copy() {
        return new JaxisTheTroublemaker(this);
    }
}

class JaxisTheTroublemakerEffect extends OneShotEffect {

    JaxisTheTroublemakerEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target creature you control. It gains haste and " +
                "\"When this creature dies, draw a card.\" Sacrifice it at the beginning of the next end step";
    }

    private JaxisTheTroublemakerEffect(final JaxisTheTroublemakerEffect effect) {
        super(effect);
    }

    @Override
    public JaxisTheTroublemakerEffect copy() {
        return new JaxisTheTroublemakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.addAdditionalAbilities(
                HasteAbility.getInstance(),
                new DiesSourceTriggeredAbility(
                        new DrawCardSourceControllerEffect(1)
                ).setTriggerPhrase("When this creature dies, ")
        );
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
