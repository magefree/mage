package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ZaskSkitteringSwarmlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("lands and cast Insect spells");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.INSECT, "another Insect you control");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent(SubType.INSECT, "Insect");

    static {
        filter.add(Predicates.or(CardType.LAND.getPredicate(), SubType.INSECT.getPredicate()));
        filter2.add(AnotherPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public ZaskSkitteringSwarmlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may play lands and cast Insect spells from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PlayLandsFromGraveyardControllerEffect(filter)
        ));

        // Whenever another Insect you control dies, put it on the bottom of its owner's library, then mill two cards.
        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(
                new PutOnLibraryTargetEffect(false, "put it on the bottom of its owner's library"),
                false, filter2, true
        );
        ability.addEffect(new MillCardsControllerEffect(2).concatBy(", then"));
        this.addAbility(ability);

        // {1}{B/G}: Target Insect gets +1/+0 and gains deathtouch until end of turn.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new BoostTargetEffect(1, 0, Duration.EndOfTurn)
                .setText("Target Insect gets +1/0"), new ManaCostsImpl<>("{1}{B/G}")
        );
        ability2.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn)
                .setText(" and gains deathtouch until end of turn"));
        ability2.addTarget(new TargetCreaturePermanent(filter3));
        this.addAbility(ability2);
    }

    private ZaskSkitteringSwarmlord(final ZaskSkitteringSwarmlord card) {
        super(card);
    }

    @Override
    public ZaskSkitteringSwarmlord copy() {
        return new ZaskSkitteringSwarmlord(this);
    }
}
