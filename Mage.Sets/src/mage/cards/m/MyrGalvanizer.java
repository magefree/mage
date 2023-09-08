package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class MyrGalvanizer extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Myr creatures");

    static {
        filter.add(SubType.MYR.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MyrGalvanizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Other Myr creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // {1}, {T}: Untap each other Myr you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MyrGalvanizerEffect(), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        this.addAbility(ability);
    }

    private MyrGalvanizer(final MyrGalvanizer card) {
        super(card);
    }

    @Override
    public MyrGalvanizer copy() {
        return new MyrGalvanizer(this);
    }
}

class MyrGalvanizerEffect extends OneShotEffect {

    MyrGalvanizerEffect() {
        super(Outcome.Untap);
        staticText = "Untap each other Myr you control";
    }

    private MyrGalvanizerEffect(final MyrGalvanizerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                MyrGalvanizer.filter, source.getControllerId(), game)) {
            if (!permanent.getId().equals(source.getSourceId())) {
                permanent.untap(game);
            }
        }
        return true;
    }

    @Override
    public MyrGalvanizerEffect copy() {
        return new MyrGalvanizerEffect(this);
    }
}
