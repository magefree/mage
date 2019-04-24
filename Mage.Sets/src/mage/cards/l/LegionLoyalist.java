package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 *
 * @author Plopman
 */
public final class LegionLoyalist extends CardImpl {

    public LegionLoyalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Haste
        this.addAbility(HasteAbility.getInstance());
        //Battalion - Whenever Legion Loyalist and at least two other creatures attack,
        //creatures you control gain first strike and trample until end of turn and can't be blocked by tokens this turn.
        Ability ability = new BattalionAbility(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent()));
        ability.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent()));
        ability.addEffect(new CantBeBlockedByTokenEffect());
        this.addAbility(ability);
    }

    public LegionLoyalist(final LegionLoyalist card) {
        super(card);
    }

    @Override
    public LegionLoyalist copy() {
        return new LegionLoyalist(this);
    }
}

class CantBeBlockedByTokenEffect extends RestrictionEffect {

    public CantBeBlockedByTokenEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures you control can't be blocked by tokens this turn";
    }

    public CantBeBlockedByTokenEffect(final CantBeBlockedByTokenEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        affectedObjectsSet = true;
        for (Permanent perm : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (affectedObjectList.contains(new MageObjectReference(permanent, game))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker instanceof PermanentToken) {
            return false;
        }
        return true;
    }

    @Override
    public CantBeBlockedByTokenEffect copy() {
        return new CantBeBlockedByTokenEffect(this);
    }
}
