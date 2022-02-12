package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrafReaver extends CardImpl {

    public GrafReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Graf Reaver exploits a creature, destroy target planeswalker.
        Ability ability = new ExploitCreatureTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPlaneswalkerPermanent());
        this.addAbility(ability);

        // At the beginning of your upkeep, Graf Reaver deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageControllerEffect(1), TargetController.YOU, false
        ));
    }

    private GrafReaver(final GrafReaver card) {
        super(card);
    }

    @Override
    public GrafReaver copy() {
        return new GrafReaver(this);
    }
}
