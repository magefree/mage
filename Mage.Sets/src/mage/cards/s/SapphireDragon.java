package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SapphireDragon extends AdventureCard {

    public SapphireDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{5}{U}{U}", "Psionic Pulse", "{2}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sapphire Dragon attacks or blocks, scry 2.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ScryEffect(2), false));

        // Psionic Pulse
        // Counter target noncreature spell.
        this.getSpellCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private SapphireDragon(final SapphireDragon card) {
        super(card);
    }

    @Override
    public SapphireDragon copy() {
        return new SapphireDragon(this);
    }
}
