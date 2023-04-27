package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author PurpleCrowbar
 */
public final class RobaranMercenaries extends CardImpl {

    public RobaranMercenaries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Robaran Mercenaries has all activated abilties of all legendary creatures you control.
        this.addAbility(new SimpleStaticAbility(new RobaranMercenariesEffect()));
    }

    private RobaranMercenaries(final RobaranMercenaries card) {
        super(card);
    }

    @Override
    public RobaranMercenaries copy() {
        return new RobaranMercenaries(this);
    }
}

class RobaranMercenariesEffect extends ContinuousEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    RobaranMercenariesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all legendary creatures you control.";
        this.addDependencyType(DependencyType.AddingAbility);
    }

    private RobaranMercenariesEffect(final RobaranMercenariesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null) {
            return false;
        }
        for (Ability ability : game.getState()
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(permanent -> permanent.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(ability -> ability.getAbilityType() == AbilityType.ACTIVATED
                        || ability.getAbilityType() == AbilityType.MANA)
                .collect(Collectors.toList())) {
            // optimization to disallow the adding of duplicate, unnecessary basic mana abilities
            if (!(ability instanceof BasicManaAbility)
                    || perm.getAbilities(game)
                    .stream()
                    .noneMatch(ability.getClass()::isInstance)) {
                perm.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public RobaranMercenariesEffect copy() {
        return new RobaranMercenariesEffect(this);
    }
}
