package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ScuttlingDoomEngine extends CardImpl {

    public ScuttlingDoomEngine(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Scuttling Doom Engine can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // When Scuttling Doom Engine dies, it deals 6 damage to target opponnent
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(6, "it"), false);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private ScuttlingDoomEngine(final ScuttlingDoomEngine card) {
        super(card);
    }

    @Override
    public ScuttlingDoomEngine copy() {
        return new ScuttlingDoomEngine(this);
    }
}
