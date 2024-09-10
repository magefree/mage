package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsiorWardwingFamiliar extends CardImpl {

    public EsiorWardwingFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast that target one or more commanders you control cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(new EsiorWardwingFamiliarEffect()));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private EsiorWardwingFamiliar(final EsiorWardwingFamiliar card) {
        super(card);
    }

    @Override
    public EsiorWardwingFamiliar copy() {
        return new EsiorWardwingFamiliar(this);
    }
}

class EsiorWardwingFamiliarEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    EsiorWardwingFamiliarEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spells your opponents cast that target one or more commanders you control cost {3} more to cast";
    }

    private EsiorWardwingFamiliarEffect(EsiorWardwingFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }

        Set<UUID> allTargets;
        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);

            // can target without cost increase
            if (allTargets.stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .anyMatch(permanent -> !filter.match(permanent, source.getControllerId(), source, game))) {
                return false;
            }
        }

        return allTargets.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> filter.match(permanent, source.getControllerId(), source, game));
    }

    @Override
    public EsiorWardwingFamiliarEffect copy() {
        return new EsiorWardwingFamiliarEffect(this);
    }
}
