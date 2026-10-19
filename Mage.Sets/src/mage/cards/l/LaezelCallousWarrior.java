package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

public final class LaezelCallousWarrior extends CardImpl {

    public LaezelCallousWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.extraDeckCard = true;
        this.addAbility(DoubleStrikeAbility.getInstance());
        Ability ability = new LaezelSpecializedTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return up to two target creature cards with total mana value 3 or less from your graveyard to the battlefield"));
        ability.addTarget(new LaezelCallousWarriorTarget());
        this.addAbility(ability);
    }

    private LaezelCallousWarrior(final LaezelCallousWarrior card) {
        super(card);
    }

    @Override
    public LaezelCallousWarrior copy() {
        return new LaezelCallousWarrior(this);
    }
}

class LaezelCallousWarriorTarget extends TargetCardInYourGraveyard {

    LaezelCallousWarriorTarget() {
        super(0, 2, StaticFilters.FILTER_CARD_CREATURE);
    }

    private LaezelCallousWarriorTarget(final LaezelCallousWarriorTarget target) {
        super(target);
    }

    @Override
    public LaezelCallousWarriorTarget copy() {
        return new LaezelCallousWarriorTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (!super.canTarget(id, source, game)) {
            return false;
        }
        int total = game.getCard(id).getManaValue();
        for (UUID target : getTargets()) {
            Card card = game.getCard(target);
            if (!target.equals(id) && card != null) {
                total += card.getManaValue();
            }
        }
        return total <= 3;
    }

    @Override
    public Set<UUID> possibleTargets(UUID controllerId, Ability source, Game game) {
        Set<UUID> targets = super.possibleTargets(controllerId, source, game);
        targets.removeIf(id -> !canTarget(id, source, game));
        return targets;
    }
}
