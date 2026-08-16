package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author muz
 */
public final class RaftSecurityOfficer extends CardImpl {

    public RaftSecurityOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}, {T}: Tap target creature. This ability costs {1} less to activate if it targets a creature with power 3 or less.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new InfoEffect(
            "This ability costs {1} less to activate if it targets a creature with power 3 or less."
        ));
        ability.setCostAdjuster(RaftSecurityOfficerAdjuster.instance);
        this.addAbility(ability);
    }

    private RaftSecurityOfficer(final RaftSecurityOfficer card) {
        super(card);
    }

    @Override
    public RaftSecurityOfficer copy() {
        return new RaftSecurityOfficer(this);
    }
}

enum RaftSecurityOfficerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            // possible
            if (CardUtil
                    .getAllPossibleTargets(ability, game)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(permanent -> permanent.isCreature() && permanent.getPower().getValue() <= 3)) {
                return;
            }
        } else {
            // real
            Permanent permanent = game.getPermanent(ability.getFirstTarget());
            if (permanent == null || !permanent.isCreature() || permanent.getPower().getValue() > 3) {
                return;
            }
        }
        CardUtil.reduceCost(ability, 1);
    }
}
