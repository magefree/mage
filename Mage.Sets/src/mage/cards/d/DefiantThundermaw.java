package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefiantThundermaw extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.DRAGON);

    public DefiantThundermaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Dragon you control attacks, it deals 2 damage to any target.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new DefiantThundermawEffect(), false, filter
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DefiantThundermaw(final DefiantThundermaw card) {
        super(card);
    }

    @Override
    public DefiantThundermaw copy() {
        return new DefiantThundermaw(this);
    }
}

class DefiantThundermawEffect extends OneShotEffect {

    DefiantThundermawEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 2 damage to any target";
    }

    private DefiantThundermawEffect(final DefiantThundermawEffect effect) {
        super(effect);
    }

    @Override
    public DefiantThundermawEffect copy() {
        return new DefiantThundermawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference mor = (MageObjectReference) getValue("attackerRef");
        if (mor == null) {
            return false;
        }
        game.damagePlayerOrPermanent(
                getTargetPointer().getFirst(game, source), 2,
                mor.getSourceId(), source, game, false, true
        );
        return true;
    }
}
