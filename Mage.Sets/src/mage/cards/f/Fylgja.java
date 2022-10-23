package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Fylgja extends CardImpl {

    public Fylgja(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Fylgja enters the battlefield with four healing counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.HEALING.createInstance(4))
                        .setText("with four healing counters on it.")));

        // Remove a healing counter from Fylgja: Prevent the next 1 damage that would be dealt to enchanted creature this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToAttachedEffect(Duration.EndOfTurn, AttachmentType.AURA, 1, false)
                .setText("Prevent the next 1 damage that would be dealt to enchanted creature this turn"),
                new RemoveCountersSourceCost(CounterType.HEALING.createInstance(1))));

        // {2}{W}: Put a healing counter on Fylgja.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.HEALING.createInstance(1)),
                new ManaCostsImpl<>("{2}{W}")));
    }

    private Fylgja(final Fylgja card) {
        super(card);
    }

    @Override
    public Fylgja copy() {
        return new Fylgja(this);
    }
}
