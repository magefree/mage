package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamberThePlunderer extends CardImpl {

    public KamberThePlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Partner with Laurine, the Diversion
        this.addAbility(new PartnerWithAbility("Laurine, the Diversion"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever a creature an opponent controls dies, you gain 1 life and create a Blood token.
        Ability ability = new DiesCreatureTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        );
        ability.addEffect(new CreateTokenEffect(new BloodToken()));
        this.addAbility(ability);
    }

    private KamberThePlunderer(final KamberThePlunderer card) {
        super(card);
    }

    @Override
    public KamberThePlunderer copy() {
        return new KamberThePlunderer(this);
    }
}
