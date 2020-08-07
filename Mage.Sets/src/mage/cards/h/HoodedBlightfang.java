package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.DestroyPlaneswalkerWhenDamagedTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class HoodedBlightfang extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public HoodedBlightfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with deathtouch attacks, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        ability.addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        this.addAbility(ability);

        // Whenever a creature you control with deathtouch deals damage to a planeswalker, destroy that planeswalker.
        this.addAbility(new DestroyPlaneswalkerWhenDamagedTriggeredAbility(filter));
    }

    private HoodedBlightfang(final HoodedBlightfang card) {
        super(card);
    }

    @Override
    public HoodedBlightfang copy() {
        return new HoodedBlightfang(this);
    }
}
