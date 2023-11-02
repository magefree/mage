package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AlphaDeathclaw extends CardImpl {

    public AlphaDeathclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Alpha Deathclaw enters the battlefield or becomes monstrous, destroy target permanent.
        TriggeredAbility trigger = new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new DestroyTargetEffect(),
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesMonstrousSourceTriggeredAbility(null)
        );
        trigger.setTriggerPhrase("When {this} enters the battlefield or becomes monstrous, ");
        trigger.addTarget(new TargetPermanent());
        this.addAbility(trigger);

        // {5}{B}{G}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{5}{B}{G}", 4));
    }

    private AlphaDeathclaw(final AlphaDeathclaw card) {
        super(card);
    }

    @Override
    public AlphaDeathclaw copy() {
        return new AlphaDeathclaw(this);
    }
}
