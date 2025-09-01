package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public class MazesMantle extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchanted creature has toxic");

    static {
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filter);

    public MazesMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.addSubType(SubType.AURA);

        //Flash
        this.addAbility(FlashAbility.getInstance());

        //Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        //When Maze’s Mantle enters the battlefield, if enchanted creature has toxic, that creature gains hexproof until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        ).setText("that creature gains hexproof until end of turn")).withInterveningIf(condition));

        //Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));
    }

    private MazesMantle(final MazesMantle card) {
        super(card);
    }

    @Override
    public MazesMantle copy() {
        return new MazesMantle(this);
    }
}
