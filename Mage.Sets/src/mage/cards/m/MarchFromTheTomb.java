package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MarchFromTheTomb extends CardImpl {

    public MarchFromTheTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{B}");

        // Return any number of target Ally creature cards with total converted mana cost of 8 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new MarchFromTheTombTarget());
    }

    private MarchFromTheTomb(final MarchFromTheTomb card) {
        super(card);
    }

    @Override
    public MarchFromTheTomb copy() {
        return new MarchFromTheTomb(this);
    }
}

class MarchFromTheTombTarget extends TargetCardInYourGraveyard {

    private static final FilterCreatureCard filterStatic = new FilterCreatureCard("Ally creature cards with total mana value 8 or less from your graveyard");
    static {
        filterStatic.add(SubType.ALLY.getPredicate());
    }

    MarchFromTheTombTarget() {
        super(0, Integer.MAX_VALUE, filterStatic);
    }

    private MarchFromTheTombTarget(final MarchFromTheTombTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 8, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 8, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }

    @Override
    public MarchFromTheTombTarget copy() {
        return new MarchFromTheTombTarget(this);
    }

}
