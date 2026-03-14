package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordCoastSerpent extends AdventureCard {

    public SwordCoastSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{5}{U}{U}", "Capsizing Wave", "{1}{U}");

        this.subtype.add(SubType.SERPENT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Sword Coast Serpent can't be blocked as long as you've cast a noncreature spell this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), CastNoncreatureSpellThisTurnCondition.instance,
                "{this} can't be blocked as long as you've cast a noncreature spell this turn"
        )).addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // Capsizing Wave
        // Return target creature to its owner's hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private SwordCoastSerpent(final SwordCoastSerpent card) {
        super(card);
    }

    @Override
    public SwordCoastSerpent copy() {
        return new SwordCoastSerpent(this);
    }
}
