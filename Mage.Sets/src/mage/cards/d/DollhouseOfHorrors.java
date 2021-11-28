package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DollhouseOfHorrors extends CardImpl {

    public DollhouseOfHorrors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {1}, {T}, Exile a creature card from your graveyard: Create a token that's a copy of the exiled card, except it's a 0/0 Construct artifact in addition to its other types and it has "This creature gets +1/+1 for each Construct you control." It gains haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DollhouseOfHorrorsEffect(), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ), true));
        this.addAbility(ability);
    }

    private DollhouseOfHorrors(final DollhouseOfHorrors card) {
        super(card);
    }

    @Override
    public DollhouseOfHorrors copy() {
        return new DollhouseOfHorrors(this);
    }
}

class DollhouseOfHorrorsEffect extends OneShotEffect {

    DollhouseOfHorrorsEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of the exiled card, except it's a 0/0 Construct artifact in " +
                "addition to its other types and it has \"This creature gets +1/+1 for each Construct you control.\" " +
                "It gains haste until end of turn";
    }

    private DollhouseOfHorrorsEffect(final DollhouseOfHorrorsEffect effect) {
        super(effect);
    }

    @Override
    public DollhouseOfHorrorsEffect copy() {
        return new DollhouseOfHorrorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), CardType.ARTIFACT, false, 1, false,
                false, null, 0, 0, false
        );
        effect.setSavedPermanent(new PermanentCard(card, source.getControllerId(), game));
        effect.setAdditionalSubType(SubType.CONSTRUCT);
        effect.addAdditionalAbilities(new SimpleStaticAbility(new BoostSourceEffect(
                ArtifactYouControlCount.instance,
                ArtifactYouControlCount.instance,
                Duration.WhileOnBattlefield
        ).setText("This creature gets +1/+1 for each artifact you control")));
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanent(), game)), source);
        return true;
    }
}
