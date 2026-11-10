package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class Type03BorgVessel extends CardImpl {

    public Type03BorgVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.SPACECRAFT);

        // Artifact spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(new FilterArtifactCard("artifact spells"), 1)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Whenever you cast a spell, create a 1/1 colorless Borg artifact creature token.
        // 5/5
        this.addAbility(new StationLevelAbility(7).withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new BorgToken()), false)).withPT(5, 5));
    }

    private Type03BorgVessel(final Type03BorgVessel card) {
        super(card);
    }

    @Override
    public Type03BorgVessel copy() {
        return new Type03BorgVessel(this);
    }
}
