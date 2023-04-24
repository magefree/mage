package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianMiteToken;

/**
 * @author TheElk801
 */
public final class Mirrex extends CardImpl {

    public Mirrex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SPHERE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Activate only if Mirrex entered the battlefield this turn.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(),
                new TapSourceCost(), MirrexCondition.instance
        ));

        // {3}, {T}: Create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new PhyrexianMiteToken()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Mirrex(final Mirrex card) {
        super(card);
    }

    @Override
    public Mirrex copy() {
        return new Mirrex(this);
    }
}

enum MirrexCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getTurnsOnBattlefield() == 0;
    }

    @Override
    public String toString() {
        return "{this} entered the battlefield this turn";
    }
}