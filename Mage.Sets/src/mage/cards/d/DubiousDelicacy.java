package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DubiousDelicacy extends CardImpl {

    public DubiousDelicacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.FOOD);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this artifact enters, up to one target creature gets -3/-3 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.
        this.addAbility(new FoodAbility());

        // {2}, {T}, Sacrifice this artifact: Target opponent loses 3 life.
        ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DubiousDelicacy(final DubiousDelicacy card) {
        super(card);
    }

    @Override
    public DubiousDelicacy copy() {
        return new DubiousDelicacy(this);
    }
}
