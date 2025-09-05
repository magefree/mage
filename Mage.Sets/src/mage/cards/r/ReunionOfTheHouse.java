package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReunionOfTheHouse extends CardImpl {

    public ReunionOfTheHouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Return any number of target creature cards with total power 10 or less from your graveyard to the battlefield. Exile Reunion of the House.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new ReunionOfTheHouseTarget());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private ReunionOfTheHouse(final ReunionOfTheHouse card) {
        super(card);
    }

    @Override
    public ReunionOfTheHouse copy() {
        return new ReunionOfTheHouse(this);
    }
}

class ReunionOfTheHouseTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filterStatic
            = new FilterCreatureCard("creature cards with total power 10 or less from your graveyard");

    ReunionOfTheHouseTarget() {
        super(0, Integer.MAX_VALUE, filterStatic);
    }

    private ReunionOfTheHouseTarget(final ReunionOfTheHouseTarget target) {
        super(target);
    }

    @Override
    public ReunionOfTheHouseTarget copy() {
        return new ReunionOfTheHouseTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, m -> m.getPower().getValue(), 10, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                m -> m.getPower().getValue(), 10, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        return super.getMessage(game) + " (selected total power " + selectedValue + ")";
    }
}
