package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RobeOfStars extends CardImpl {

    public RobeOfStars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+3.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(0, 3)));

        // Astral Projection — {1}{W}: Equipped creature phases out.
        this.addAbility(new SimpleActivatedAbility(
                new RobeOfStarsEffect(), new ManaCostsImpl<>("{1}{W}")
        ).withFlavorWord("Astral Projection"));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private RobeOfStars(final RobeOfStars card) {
        super(card);
    }

    @Override
    public RobeOfStars copy() {
        return new RobeOfStars(this);
    }
}

class RobeOfStarsEffect extends OneShotEffect {

    RobeOfStarsEffect() {
        super(Outcome.Exile);
        staticText = "equipped creature phases out";
    }

    private RobeOfStarsEffect(final RobeOfStarsEffect effect) {
        super(effect);
    }

    @Override
    public RobeOfStarsEffect copy() {
        return new RobeOfStarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        Permanent equipped = game.getPermanent(permanent.getAttachedTo());
        return equipped != null && equipped.phaseOut(game);
    }
}
